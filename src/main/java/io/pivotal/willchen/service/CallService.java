package io.pivotal.willchen.service;

import io.pivotal.willchen.domain.Payload;
import io.pivotal.willchen.domain.Person;
import io.pivotal.willchen.domain.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Optional;

/**
 * Created by chenw13 on 09/12/2016.
 */
@RestController
@Service
public class CallService {

    private JAXBContext context;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public CallService( ) {
        try {
            context = JAXBContext.newInstance(Person.class);
            marshaller = context.createMarshaller();
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/person", method = RequestMethod.POST,headers="Accept=application/xml")
    public ResponseEntity me(@RequestBody Person p) {

        Person user = new Person();
        user.setId("0");
        user.setName(p.getName());

        return Optional.ofNullable(user)
                .map(a -> new ResponseEntity<Person>(a, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/person1", method = RequestMethod.POST, headers = "Accept=application/xml")
    public ResponseEntity me1( @RequestBody Payload p) {

        StringReader sr = new StringReader(p.getPayload());
        Person user = null;
        try {
            user = (Person)unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        user.setName("MODIFIED");

        final String resultString = user.toString();

        System.out.println("resultString is====================="+resultString);

        Payload payload = new Payload();
        payload.setPayload(resultString);

        return Optional.ofNullable(user)
                .map(a -> new ResponseEntity<Payload>(payload, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/person2", method = RequestMethod.POST, headers = "Accept=application/xml")
    public String me1( @RequestBody String p) {

        StringReader sr = new StringReader(p);
        Person user = null;
        try {
            user = (Person)unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        user.setName("MODIFIED");

        final String resultString = user.toString();

        System.out.println("resultString is====================="+resultString);

        return resultString;

//        return Optional.ofNullable(user)
//                .map(a -> new ResponseEntity<String>(resultString, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/person3", method = RequestMethod.POST)
    public ResponseEntity me2( @RequestBody Test test) {

        Test test1 = new Test();
        test1.setId(10);
        test1.setName(test.getName());
        return Optional.ofNullable(test1)
                .map(a -> new ResponseEntity<Test>(test1, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
