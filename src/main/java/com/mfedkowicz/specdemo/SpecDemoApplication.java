/**
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mfedkowicz.specdemo;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.*;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Mateusz Fedkowicz
 */
@SpringBootApplication
@Import(SpecDemoApplication.Config.class)
public class SpecDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecDemoApplication.class, args);
    }

    @Configuration
    static class Config implements WebMvcConfigurer {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new SpecificationArgumentResolver());
            argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
            argumentResolvers.add(pagedResourcesAssemblerArgumentResolver());
        }

        HateoasPageableHandlerMethodArgumentResolver pageableResolver() {
            return new HateoasPageableHandlerMethodArgumentResolver(sortResolver());
        }

        HateoasSortHandlerMethodArgumentResolver sortResolver() {
            return new HateoasSortHandlerMethodArgumentResolver();
        }

        PagedResourcesAssemblerArgumentResolver pagedResourcesAssemblerArgumentResolver() {
            return new PagedResourcesAssemblerArgumentResolver(pageableResolver(), null);
        }

    }

}
