package io.pivotal.willchen.controller;

import io.pivotal.willchen.domain.Payload;
import io.pivotal.willchen.domain.Person;
import io.pivotal.willchen.domain.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by chenw13 on 09/12/2016.
 */
@RestController
public class CallPerson {

    private JAXBContext context;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public CallPerson( ) {

        try {
            Class[] arr = new Class[2];
            arr[0] = Person.class;
            arr[1] = CallPerson.class;

            context = JAXBContext.newInstance(arr, null);
            marshaller = context.createMarshaller();
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/call")
    public ResponseEntity getCall(@RequestParam("name") String name ) {

        Person p = new Person();
        p.setId("-1");
        p.setName(name);

        RestTemplate restTemplate = new RestTemplate();
        Person emp = restTemplate.postForObject("http://localhost:8080/person", p, Person.class);


        //return to callee, use marshell to marshal to xml string
        StringWriter sw = new StringWriter();
        String returnString = "";
        try {
            marshaller.marshal(emp, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        sw.write(returnString);

        System.out.println(sw.toString());


        return Optional.ofNullable(emp)
                .map(a -> new ResponseEntity<Person>(emp, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/call1")
    public ResponseEntity getCall1(@RequestParam("name") String name ) {
        Person p = new Person();
        p.setId("-1");
        p.setName(name);

        StringWriter inputSW = new StringWriter();

        String inputString = "";
        try {
            marshaller.marshal(p, inputSW);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        inputSW.write(inputString);

        System.out.println("Call1 input xml is:"+inputSW.toString());

        Payload payload = new Payload();
        payload.setPayload(inputSW.toString());

        RestTemplate restTemplate = new RestTemplate();

        Payload emp = restTemplate.postForObject("http://localhost:8080/person1", payload, Payload.class);

        System.out.println("Call1 out xml is:"+emp.getPayload());

        return Optional.ofNullable(emp)
                .map(a -> new ResponseEntity<String>(emp.getPayload(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @RequestMapping(method = RequestMethod.GET, value = "/call2")
    public ResponseEntity getCall2(@RequestParam("name") String name ) {

        Person p = new Person();
        p.setId("-1");
        p.setName(name);
        List<Test> testarr = new ArrayList<Test>();
        Test t1 = new Test();
        t1.setId(1);
        t1.setName(p.getName());
        Test t2 = new Test();
        t1.setId(2);
        t1.setName(p.getName());
        testarr.add(t1);
        testarr.add(t2);
        p.setTest(testarr);

        StringWriter inputSW = new StringWriter();

        String inputString = "";
        try {
            marshaller.marshal(p, inputSW);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        inputSW.write(inputString);

        System.out.println(inputSW.toString());

        System.out.println("Call2 input xml is:"+inputSW.toString());

        RestTemplate restTemplate = new RestTemplate();
        String emp = restTemplate.postForObject("http://localhost:8080/person2", inputSW.toString(), String.class);

        System.out.println("Call2 output xml is:"+emp);

        return Optional.ofNullable(emp)
                .map(a -> new ResponseEntity<String>(emp, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @RequestMapping(method = RequestMethod.GET, value = "/call3")
    public ResponseEntity getCall3(@RequestParam("name") String name ) {

        Test test = new Test();
        test.setId(1);
        test.setName("TEST");
        RestTemplate restTemplate = new RestTemplate();
        Test emp = restTemplate.postForObject("http://localhost:8080/person3", test, Test.class);

        return Optional.ofNullable(emp)
                .map(a -> new ResponseEntity<Test>(emp, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

}
