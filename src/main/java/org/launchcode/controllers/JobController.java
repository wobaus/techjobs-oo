package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Name;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController{

    private JobData jobData = JobData.getInstance();


    @RequestMapping(value = "/", method = RequestMethod.GET)

    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job job =  jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)

    public String add(@ModelAttribute @Valid JobForm jobForm, Errors errors, Model model, RedirectAttributes redirectAttributes) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

            Job newJob = new Job();

            if (errors.hasErrors()) {
                return "new-job";
            }

                Employer newEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
                Location newLocation = jobData.getLocations().findById(jobForm.getLocation());
                PositionType newPosition = jobData.getPositionTypes().findById(jobForm.getPosition());
                CoreCompetency newSkill = jobData.getCoreCompetencies().findById(jobForm.getSkill());

                newJob.setName(jobForm.getName());
                newJob.setEmployer(newEmployer);
                newJob.setLocation(newLocation);
                newJob.setPositionType(newPosition);
                newJob.setCoreCompetency(newSkill);


                jobData.add(newJob);
//                redirectAttributes.addFlashAttribute("newJob", newJob);



        return "redirect:/job?id=" + newJob.getId();
    }
}
