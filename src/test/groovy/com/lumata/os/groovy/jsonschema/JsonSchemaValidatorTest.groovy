/*
 * 2012 copyright Buongiorno SpA
 */
package com.lumata.os.groovy.jsonschema;

import static org.junit.Assert.*;
import groovy.json.JsonSlurper

import org.junit.Test



/**
 * @author Alexander De Leon - alexander.leon@lumatagroup.com
 *
 */
class JsonSchemaValidatorTest {

	@Test
	void testJson(){
		//create json object to validate
		def valid = new JsonSlurper().parseText('{"a":1}')
		def invalid = new JsonSlurper().parseText('{"a":"a"}')

		//create json schema
		def jsonSchema = new JsonSlurper().parseText('{"properties":{"a":{"type":"number"}}}')

		use(JsonSchemaValidator){
			valid.setSchema(jsonSchema)
			invalid.setSchema(jsonSchema)
			assert valid.validate() : "Json is not valid"
			assert !invalid.validate() : "Json is  valid"
		}
	}

	@Test
	void testValidObject(){
		//create json object to validate
		def jsonDoc = new TestObj()
		jsonDoc.a = 1

		//create json schema
		def jsonSchema = new JsonSlurper().parseText('{"properties":{"a":{"type":"number"}}}')

		use(JsonSchemaValidator){
			jsonDoc.setSchema(jsonSchema)
			assert jsonDoc.validate() : "Json is not valid"
		}
	}


	@Test
	void testNestedProperties(){
		//create json object to validate
		def valid = new JsonSlurper().parseText('{"a":{"b":1}}')
		def invalid = new JsonSlurper().parseText('{"a":1}')

		//create json schema
		def jsonSchema = new JsonSlurper().parseText('{"properties":{"a":{"type":"object", "properties":{"b":{"type":"number"}}}}}')

		use(JsonSchemaValidator){
			valid.setSchema(jsonSchema)
			invalid.setSchema(jsonSchema)
			assert valid.validate() : "Json is not valid"
			assert !invalid.validate() : "Json is valid"
		}
	}

	@Test
	void testArrays(){
		//create json object to validate
		def valid = new JsonSlurper().parseText('{"a":[1,2]}')
		def invalid = new JsonSlurper().parseText('{"a":1}')

		//create json schema
		def jsonSchema = new JsonSlurper().parseText('{"properties":{"a":{"type":"array", "items":{"type":"number"}}}}')

		use(JsonSchemaValidator){
			valid.setSchema(jsonSchema)
			invalid.setSchema(jsonSchema)
			assert valid.validate() : "Json is not valid"
			assert !invalid.validate() : "Json is valid"
		}
	}


	class TestObj{
		int a;
	}


}
