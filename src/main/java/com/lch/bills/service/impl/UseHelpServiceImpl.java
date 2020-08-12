package com.lch.bills.service.impl;

import com.lch.bills.pojo.UseHelp;
import com.lch.bills.repo.UseHelpRepo;
import com.lch.bills.service.UseHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UseHelpServiceImpl implements UseHelpService {

    @Autowired
    private UseHelpRepo useHelpRepo;

    @Override
    public List<UseHelp> getUseHelp() {
        return useHelpRepo.getUseHelp();
    }
}
