package gregoire.kevin.location_crud.controller;

import gregoire.kevin.location_crud.dto.PlaneDto;
import gregoire.kevin.location_crud.model.Plane;
import gregoire.kevin.location_crud.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planes")
public class PlaneController {

    @Autowired
    private PlaneService planeService;

    private PlaneDto convertToDto(Plane plane) {
        return new PlaneDto(
                plane.getId(),
                plane.getModel(),
                plane.getBrand(),
                plane.getReleaseDate(),
                plane.getStatus().toString()
        );
    }

    private Plane convertToEntity(PlaneDto planeDto) {
        Plane plane = new Plane();
        plane.setId(planeDto.getId());
        plane.setModel(planeDto.getModel());
        plane.setBrand(planeDto.getBrand());
        plane.setReleaseDate(planeDto.getReleaseDate());
        plane.setStatus(Plane.Status.valueOf(planeDto.getStatus()));
        return plane;
    }

    @GetMapping
    public List<PlaneDto> getAllPlanes() {
        return planeService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlaneDto getPlaneById(@PathVariable Long id) {
        Plane plane = planeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Avion non trouvé"));
        return convertToDto(plane);
    }

    @PostMapping
    public PlaneDto createPlane(@RequestBody PlaneDto planeDto) {
        Plane plane = convertToEntity(planeDto);
        Plane savedPlane = planeService.save(plane);
        return convertToDto(savedPlane);
    }

    @PutMapping("/{id}")
    public PlaneDto updatePlane(@PathVariable Long id, @RequestBody PlaneDto updatedPlaneDto) {
        Plane plane = planeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Avion non trouvé"));

        plane.setModel(updatedPlaneDto.getModel());
        plane.setBrand(updatedPlaneDto.getBrand());
        plane.setReleaseDate(updatedPlaneDto.getReleaseDate());
        plane.setStatus(Plane.Status.valueOf(updatedPlaneDto.getStatus()));

        Plane updatedPlane = planeService.save(plane);
        return convertToDto(updatedPlane);
    }

    @DeleteMapping("/{id}")
    public void deletePlane(@PathVariable Long id) {
        planeService.deleteById(id);
    }
}