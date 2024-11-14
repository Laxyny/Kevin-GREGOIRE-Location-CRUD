package gregoire.kevin.location_crud.controller;

import gregoire.kevin.location_crud.dto.PlaneDto;
import gregoire.kevin.location_crud.model.Plane;
import gregoire.kevin.location_crud.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/planes")
public class PlaneViewController {

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
    public String listPlanes(Model model) {
        List<PlaneDto> planes = planeService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("planes", planes);
        return "planes/list";  // correspond à src/main/resources/templates/planes/list.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("plane", new PlaneDto());
        return "planes/add";
    }

    @PostMapping("/add")
    public String addPlane(@ModelAttribute PlaneDto planeDto) {
        Plane plane = convertToEntity(planeDto);
        planeService.save(plane);
        return "redirect:/planes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Plane plane = planeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Avion non trouvé"));
        model.addAttribute("plane", convertToDto(plane));
        return "planes/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePlane(@PathVariable Long id, @ModelAttribute PlaneDto updatedPlaneDto) {
        Plane plane = planeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Avion non trouvé"));
        plane.setModel(updatedPlaneDto.getModel());
        plane.setBrand(updatedPlaneDto.getBrand());
        plane.setReleaseDate(updatedPlaneDto.getReleaseDate());
        plane.setStatus(Plane.Status.valueOf(updatedPlaneDto.getStatus()));
        planeService.save(plane);
        return "redirect:/planes";
    }

    @GetMapping("/delete/{id}")
    public String deletePlane(@PathVariable Long id) {
        planeService.deleteById(id);
        return "redirect:/planes";
    }

    @GetMapping("/{id}")
    public String viewPlane(@PathVariable Long id, Model model) {
        Plane plane = planeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Avion non trouvé"));
        model.addAttribute("plane", convertToDto(plane));
        return "planes/view";
    }
}
