package com.github.wenhao.jpa.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.wenhao.jpa.Specifications;
import com.github.wenhao.jpa.builder.PersonBuilder;
import com.github.wenhao.jpa.model.Person;
import com.github.wenhao.jpa.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NotEqualTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void should_be_able_to_find_by_using_not_equal() {
        // given
        Person jack = new PersonBuilder()
            .name("Jack")
            .company("company-name")
            .build();
        Person eric = new PersonBuilder()
            .name("Eric")
            .company("company-name")
            .build();
        personRepository.save(jack);
        personRepository.save(eric);

        // when
        Specification<Person> specification = Specifications.<Person>builder()
            .eq("company", "company-name")
            .ne("name", jack.getName())
            .build();

        Person result = personRepository.findOne(specification);

        // then
        assertThat(result.getName()).isEqualTo(eric.getName());
    }
}
