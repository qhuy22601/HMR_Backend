package com.example.capstoneprj.service;


import com.example.capstoneprj.domain.model.Salary;
import com.example.capstoneprj.domain.model.UserModel;
import com.example.capstoneprj.repository.SalaryRepo;
import com.example.capstoneprj.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    private ScheduledTaskRegistrar taskRegistrar;
    private ScheduledTask scheduledFuture;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private SalaryService salaryService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
    }

    public void enableAutoPaySalary() {
        scheduledFuture = taskRegistrar.scheduleCronTask(new CronTask(() -> {
            List<Salary> salaryList = salaryRepo.findAll();
            for(Salary salary: salaryList){
                for (String userId : salary.getListUser()) {
                    Optional<UserModel> newUser = userRepo.findById(userId);
                    UserModel userModel = newUser.get();
                    userModel.setBalance(userModel.getBalance() + userModel.getPayGrade().getValue());
                    userRepo.save(userModel);
                }
            }
        }, "0 0 1 * * ?")); // run every 1st day of the month
//        },"0 * * * * ?"));//run every min
    }

    public void disableAutoPaySalary() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }
}
