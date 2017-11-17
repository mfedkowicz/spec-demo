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
package com.mfedkowicz.specdemo.web;

import com.mfedkowicz.specdemo.data.PersonEntity;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Mateusz Fedkowicz
 */
@Component
public class PersonResourceAssembler extends ResourceAssemblerSupport<PersonEntity, PersonResource> {

    static URI selfUri(PersonEntity personEntity) {
        return selfRefBuilder(personEntity).toUri();
    }

    private static ControllerLinkBuilder selfRefBuilder(PersonEntity personEntity) {
        return linkTo(methodOn(PersonController.class).get(personEntity.getId()));
    }

    public PersonResourceAssembler() {
        super(PersonController.class, PersonResource.class);
    }

    @Override
    public PersonResource toResource(PersonEntity entity) {
        PersonResource personResource = new PersonResource(entity.getName(), entity.getSurname());
        personResource.add(selfRefBuilder(entity).withSelfRel());
        return personResource;
    }

}
