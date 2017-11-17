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
import com.mfedkowicz.specdemo.data.PersonRepository;
import com.mfedkowicz.specdemo.data.PersonSpecification;
import com.mfedkowicz.specdemo.exception.PersonNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import static com.mfedkowicz.specdemo.web.PersonResourceAssembler.selfUri;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Mateusz Fedkowicz
 */
@RestController
@RequestMapping("/person")
class PersonController {

    private final PersonRepository repository;
    private final PersonResourceAssembler personResourceAssembler;

    public PersonController(PersonRepository repository, PersonResourceAssembler personResourceAssembler) {
        this.repository = repository;
        this.personResourceAssembler = personResourceAssembler;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid PersonResource person) {
        PersonEntity createdPerson = repository.save(new PersonEntity(
                person.getName(),
                person.getSurname()
        ));
        return ResponseEntity.created(selfUri(createdPerson)).build();
    }

    @GetMapping("/{id}")
    public PersonResource get(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(personResourceAssembler::toResource)
                .orElseThrow(PersonNotFoundException::new);
    }

    @GetMapping
    public PagedResources<PersonResource> list(PersonSpecification spec, Pageable pageable, PagedResourcesAssembler<PersonEntity> assembler) {
        Page<PersonEntity> personPage = repository.findAll(spec, pageable);
        return assembler.toResource(personPage, personResourceAssembler);
    }

}
