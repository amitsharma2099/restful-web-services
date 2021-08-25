package com.example.demo.filtering;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	//Static filtering using @JsonIgnore in bean
	@GetMapping("/static-filtering")
	public SomeBean retrieveSomeBean() {
		return new SomeBean("value1", "value2", "value3", "value4");
	}
	
	//Static filtering using @JsonIgnore in bean
	@GetMapping("/static-filtering-list")
	public List<SomeBean> retrieveListOfSomeBeans() {
		return Arrays.asList(new SomeBean("value1", "value2", "value3", "value4"),
							 new SomeBean("value11", "value22", "value33", "value44"));
	}
	
	//Dynamic Filtering: return only field1 and field2
	@GetMapping("/dynamic-filtering")
	public MappingJacksonValue retrieveSomeOtherBean() {
		SomeOtherBean someOtherBean = new SomeOtherBean("value1", "value2", "value3", "value4");
		MappingJacksonValue mappingJacksonValue = filterOutFields(List.of(someOtherBean), "field1", "field2");
		return mappingJacksonValue;
	}
	
	//Dynamic Filtering: return only field3 and field4
	@GetMapping("/dynamic-filtering-list")
	public MappingJacksonValue retrieveListOfSomeOtherBeans() {
		List<SomeOtherBean> someOtherBeans = Arrays.asList(new SomeOtherBean("value1", "value2", "value3", "value4"),
							 new SomeOtherBean("value11", "value22", "value33", "value44"));
		
		MappingJacksonValue mappingJacksonValue = filterOutFields(someOtherBeans, "field1", "field3", "field4");
		return mappingJacksonValue;
	}
	
	private MappingJacksonValue filterOutFields(List<SomeOtherBean> someOtherBeans, String...fields) {
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
		//"SomeOtherBeanFilter" is the name mentioned as filter at class level inside bean named "someOtherBean" as @JsonFilter("SomeOtherBeanFilter")
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeOtherBeanFilter", filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someOtherBeans);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
}
