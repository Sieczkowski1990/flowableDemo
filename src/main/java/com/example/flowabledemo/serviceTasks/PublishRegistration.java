package com.example.flowabledemo.serviceTasks;

import com.example.flowabledemo.entity.Registration;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class PublishRegistration implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("Publishing Registration");
    }
}
