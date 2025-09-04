package com.kitt51.PetVax.Vaccine.Command.Job;

import com.kitt51.PetVax.Email.Email;
import com.kitt51.PetVax.Email.EmailService;
import com.kitt51.PetVax.Vaccine.Command.Domain.Vaccine;
import com.kitt51.PetVax.Vaccine.Command.Domain.VaccineCommandRepository;
import com.kitt51.PetVax.Vaccine.Command.Domain.VaccineEmailDTO;
import com.kitt51.PetVax.Vaccine.Query.Service.VaccineQueryService;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SendEmailJob  {

    private final VaccineCommandRepository commandRepository;
    private final EmailService emailService;

    public SendEmailJob(VaccineCommandRepository commandRepository, EmailService emailService) {
        this.commandRepository = commandRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 6 * * * ")
    public void sendEmailFutureScheduledVaccine(){
        var vaccines = commandRepository.getEmailData(LocalDate.now());
        for(VaccineEmailDTO vaccine: vaccines){
            Email email = new Email();
            email.setRecipient(vaccine.email());
            email.setMsgBody("Vaccine for: " + vaccine.petName() + " is coming on: " + vaccine.date());
            email.setSubject("Vaccine");
            emailService.sendSimpleEmail(email);
        }
    }
}
