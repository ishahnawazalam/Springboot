package com.demo.first;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppArgsRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Options: " + args.getOptionNames());
    }
}
// u can pass this option: "--foo=bar --debug" in program arguments i.e., click edit configuration then paste in argument wala field then apply,ok and run
