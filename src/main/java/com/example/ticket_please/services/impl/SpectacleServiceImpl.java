package com.example.ticket_please.services.impl;

import com.example.ticket_please.Entity.Repertoire;
import com.example.ticket_please.Entity.Spectacle;
import com.example.ticket_please.repo.RepertoireRepository;
import com.example.ticket_please.repo.SpectacleRepository;
import com.example.ticket_please.services.SpectacleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpectacleServiceImpl implements SpectacleService {

    private final SpectacleRepository spectacleRepository;
    private final RepertoireRepository repertoireRepository;

    @Override
    public String getSpectacles(final Model model) {
        final List<Spectacle> spectacles = spectacleRepository.findAll();
        model.addAttribute("spectacles", spectacles);
        return "spectacleIndex";
    }

    @Override
    public String addSpectacle(final Spectacle spectacle, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "add-spectacle";
        }
        spectacleRepository.save(spectacle);
        log.info("A new show has been added to the database " + spectacle.getTitle());
        return "redirect:/spectacles/list";
    }

    @Override
    public String showUpdateFormSpectacle(final long id, final Model model) {
        final Spectacle spectacle = spectacleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect ID: " + id));
        model.addAttribute("spectacle", spectacle);
        return "update-spectacle";
    }

    @Override
    public String updateSpectacle(final long id, final Spectacle spectacle) {
        final Spectacle spectacleFromDb = spectacleRepository.getOne(id);
        spectacleFromDb.setDescription(spectacle.getDescription());
        spectacleFromDb.setImageUrl(spectacle.getImageUrl());
        spectacleFromDb.setLength(spectacle.getLength());
        spectacleFromDb.setMinAge(spectacle.getMinAge());
        spectacleFromDb.setTitle(spectacle.getTitle());
        log.info("Show data edited\n" + spectacle.getTitle());
        return "redirect:/spectacles/list";
    }

    @Override
    public String deleteSpectacle(final long id, final Model model) {
        final List<Repertoire> repertoires = repertoireRepository.findBySpectacleId(id);
        repertoires.forEach(r -> repertoireRepository.deleteById(r.getId()));
        final Spectacle spectacle = spectacleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect ID : " + id));
        spectacleRepository.delete(spectacle);
        final List<Spectacle> spectacles = spectacleRepository.findAll();
        model.addAttribute("spectacles", spectacles);
        log.info("The show has been removed " + spectacle.getTitle());
        return "spectacleIndex";
    }

    @Override
    public String showSpectacleRepertoireForm(final String spectacleName, final Model model) {
        final Spectacle spectacleRepertoire = spectacleRepository.findByTitle(spectacleName);
        model.addAttribute("spectacleRepertoire", spectacleRepertoire);
        model.addAttribute("repertoire", new Repertoire());
        return "spectacle-repertoire";
    }

    @Override
    public String addSpectacleRepertoire(final Repertoire repertoire, final Long spectacleId, final BindingResult result) {
        repertoire.setSpectacle(spectacleRepository.getOne(spectacleId));
        repertoireRepository.save(repertoire);
        log.info("Added repertoire for the show with ID " + spectacleId);
        return "redirect:/spectacles/list";
    }

    @Override
    public String showUpdateSpectacleRepertoireForm(final String spectacleName, final Long repertoireId, final Model model) {
        final Repertoire repertoire = repertoireRepository.getOne(repertoireId);
        final Spectacle spectacleRepertoire = spectacleRepository.findByTitle(spectacleName);
        model.addAttribute("spectacleRepertoire", spectacleRepertoire);
        model.addAttribute("repertoire", repertoire);
        return "spectacle-repertoire";
    }

    @Override
    public String updateSpectacleRepertoire(final Repertoire repertoire, final Long repertoireId, final BindingResult result) {
        final Repertoire repertoireFromDb = repertoireRepository.getOne(repertoireId);
        repertoireFromDb.setDate(repertoire.getDate());
        log.info("Updated repertoire data for " + repertoire.getSpectacle().getTitle());
        return "redirect:/spectacles/list";
    }

    @Override
    public String deleteSpectacleRepertoire(final Long repertoireId, final Model model) {
        repertoireRepository.deleteById(repertoireId);
        log.info("Removed repertoire with ID" + repertoireId);
        return "redirect:/spectacles/list";
    }
}