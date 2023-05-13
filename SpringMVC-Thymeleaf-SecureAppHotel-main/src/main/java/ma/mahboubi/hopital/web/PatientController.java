package ma.mahboubi.hopital.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.mahboubi.hopital.entities.Patient;
import ma.mahboubi.hopital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String index (Model model,
                          @RequestParam(name = "keyword",defaultValue ="") String kw,
                          @RequestParam(name = "page",defaultValue = "0") int p,
                          @RequestParam(name = "size",defaultValue = "4") int s) {
        Page<Patient> patientPage = patientRepository.findByNameContains(kw, PageRequest.of(p, s));
        model.addAttribute("listPatients", patientPage.getContent());
        model.addAttribute("pages",new int[patientPage.getTotalPages()]);
        model.addAttribute("currentPage",p);
        model.addAttribute("keyword", kw);
        model.addAttribute("sise",new int[(int)  patientPage.getTotalElements()]);
        return "patients";

    }
    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/formPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatient";
    }
    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors())return "formPatient";
        patientRepository.save(patient);
        return "redirect:/user/index?keyword="+patient.getName();
    }
    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(Model model, @RequestParam(name = "id") Long id){
        Patient patient=patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
}
