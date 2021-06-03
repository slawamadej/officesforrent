package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gabinetynagodziny.officesforrent.entity.Detail;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.service.DetailService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/details")
public class DetailController {

    private final DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping
    public String details(Model model){
        List<Detail> details = detailService.findAll();
        model.addAttribute("details", details);
        return "details";
    }

    @GetMapping("/add")
    public String addDetail(Model model){
        Detail detail = new Detail();
        model.addAttribute("detail", detail);
        return "adddetail";
    }

    @ResponseBody
    @PostMapping("/add")
    public String addDetailPost(@Valid Detail detail, BindingResult result, Model model){
        Detail detailSaved = detailService.mergeDetail(detail);
        return "dodano detail";
    }

   // @ResponseBody
    @GetMapping("/{id}")
    public String detailId(@PathVariable("id") Long id, Model model){
        Optional<Detail> optionalDetail= detailService.findByDetailId(id);

        if(optionalDetail.isEmpty()){
            return "nie ma detaila o takim id";
        }
        model.addAttribute("detail", optionalDetail.get());
        return "adddetail";
    }

}
