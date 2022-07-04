package ru.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.project.model.dto.animal.AddAnimalDto;
import ru.project.model.dto.animal.DeleteAnimalDto;
import ru.project.model.dto.animal.UpdateAnimalDto;
import ru.project.model.entity.Animal;
import ru.project.model.entity.OAuthAccessToken;
import ru.project.service.AnimalService;
import ru.project.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;
    private final TokenService tokenService;

    public AnimalController(AnimalService animalService, TokenService tokenService) {
        this.animalService = animalService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimalsByUser(HttpServletRequest request) {
        OAuthAccessToken token = tokenService.getTokenFromRequest(request);
        String username = token.getUsername();
        return ResponseEntity.ok(animalService.getAllAnimalsByUser(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalInfoById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getInfoByAnimalId(id));
    }

    @PostMapping
    public ResponseEntity<Animal> addAnimal(HttpServletRequest request, @RequestBody AddAnimalDto addAnimalDto) {
        if (addAnimalDto.getOwner() == null || addAnimalDto.getOwner().isEmpty() || addAnimalDto.getOwner().equals("")) {
            OAuthAccessToken token = tokenService.getTokenFromRequest(request);
            String username = token.getUsername();
            addAnimalDto.setOwner(username);
        }
        return ResponseEntity.ok(animalService.add(addAnimalDto));
    }

    @PutMapping
    public ResponseEntity<Animal> updateAnimalInfo(HttpServletRequest request, @RequestBody UpdateAnimalDto updateAnimalDto) {
        if (updateAnimalDto.getOwner() == null || updateAnimalDto.getOwner().isEmpty() || updateAnimalDto.getOwner().equals("")) {
            OAuthAccessToken token = tokenService.getTokenFromRequest(request);
            String username = token.getUsername();
            updateAnimalDto.setOwner(username);
        }
        return ResponseEntity.ok(animalService.update(updateAnimalDto));
    }

    @DeleteMapping
    public ResponseEntity<Animal> deleteAnimal(HttpServletRequest request, @RequestBody DeleteAnimalDto deleteAnimalDto) {
        if (deleteAnimalDto.getOwner() == null || deleteAnimalDto.getOwner().isEmpty() || deleteAnimalDto.getOwner().equals("")) {
            OAuthAccessToken token = tokenService.getTokenFromRequest(request);
            String username = token.getUsername();
            deleteAnimalDto.setOwner(username);
        }
        return ResponseEntity.ok(animalService.delete(deleteAnimalDto));
    }


}
