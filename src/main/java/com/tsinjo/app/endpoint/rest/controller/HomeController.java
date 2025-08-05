package com.tsinjo.app.endpoint.rest.controller;

import com.tsinjo.app.domain.Donation;
import com.tsinjo.app.domain.Help;
import com.tsinjo.app.repository.DonationRepository;
import com.tsinjo.app.repository.HelpRepository;
import com.tsinjo.app.endpoint.event.EventProducer;
import com.tsinjo.app.endpoint.event.model.VerifyPaymentRequested;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {
	private final DonationRepository donationRepository;
	private final HelpRepository helpRepository;
	private final EventProducer<VerifyPaymentRequested> eventProducer;

	public HomeController(DonationRepository donationRepository, HelpRepository helpRepository,
			EventProducer<VerifyPaymentRequested> eventProducer) {
		this.donationRepository = donationRepository;
		this.helpRepository = helpRepository;
		this.eventProducer = eventProducer;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Donation> donations = donationRepository.findAll();
		List<Help> helps = helpRepository.findAll();
		model.addAttribute("donations", donations);
		model.addAttribute("helps", helps);
		model.addAttribute("donationForm", new Donation());
		return "index";
	}

	@PostMapping("/donate")
	public String donate(@ModelAttribute Donation donation) {
		donationRepository.save(donation);
		// Produire l'événement de vérification paiement si applicable
		if (donation.getPayment() != null) {
			VerifyPaymentRequested event = VerifyPaymentRequested.builder()
					.apiKey(System.getenv("VOLAPI_KEY")) // à adapter selon la source de la clé
					.pspPaymentId(donation.getPayment().getPspPaymentId())
					.payerEmail(donation.getPayment().getPayerEmail())
					.pspType(donation.getPayment().getPspType())
					.build();
			eventProducer.accept(List.of(event));
		}
		return "redirect:/";
	}
}
