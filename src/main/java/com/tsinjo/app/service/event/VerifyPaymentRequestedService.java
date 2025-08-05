package com.tsinjo.app.service.event;

import com.tsinjo.app.endpoint.event.model.VerifyPaymentRequested;
import com.tsinjo.app.domain.Payment;
import com.tsinjo.app.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VerifyPaymentRequestedService implements Consumer<VerifyPaymentRequested> {
	private final PaymentRepository paymentRepository;
	private final RestTemplate restTemplate = new RestTemplate();
	private final String volaApiUrl = "https://42cwka3n4ifcp7ufheyrpmph240iuaxo.lambda-url.eu-west-3.on.aws/payment";

	@Override
	public void accept(VerifyPaymentRequested event) {
		String url = volaApiUrl + "?apiKey=" + event.getApiKey()
				+ "&payerEmail=" + event.getPayerEmail()
				+ "&pspType=" + event.getPspType()
				+ "&pspPaymentId=" + event.getPspPaymentId();
		ResponseEntity<PaymentStatusResponse> response = restTemplate.getForEntity(url, PaymentStatusResponse.class);
		if (response.getBody() != null) {
			String status = response.getBody().getVerificationStatus();
			Payment payment = paymentRepository.findById(Long.parseLong(event.getPspPaymentId()));
			if (payment != null) {
				payment.setStatus(status);
				paymentRepository.save(payment);
			}
		}
	}

	// DTO pour mapper la réponse JSON de Vola
	public static class PaymentStatusResponse {
		private String verificationStatus;

		public String getVerificationStatus() {
			return verificationStatus;
		}

		public void setVerificationStatus(String verificationStatus) {
			this.verificationStatus = verificationStatus;
		}
	}
}
