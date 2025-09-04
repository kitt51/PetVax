package com.kitt51.PetVax;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModularityTest {
    static ApplicationModules modules = ApplicationModules.of(PetVaxApplication.class);

    @Test
    void verifiesModularStructure(){
        modules.verify();
        modules.forEach(System.out::println);
    }

    @Test
    void createModuleDocumentation(){
        new Documenter(modules).writeDocumentation();
    }
}
