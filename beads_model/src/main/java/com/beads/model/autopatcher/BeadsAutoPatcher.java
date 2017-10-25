package com.beads.model.autopatcher;

import com.tacitknowledge.util.migration.jdbc.AutoPatchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class BeadsAutoPatcher {

    @Resource(name="dataSource")
    private DataSource dataSource;



}
