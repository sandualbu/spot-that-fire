/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;


import java.util.Arrays;

@Controller
@SpringBootApplication
public class Main {

  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://heroku_82dgqrs8:rmnk6nifg8tpetr43mfl00eo9c@ds125058.mlab.com:25058/heroku_82dgqrs8"));
  //MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017/nasa"));

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  // index mapping
  @RequestMapping("/")
  String index() {
    return "index";
  }

  // index mapping
  @RequestMapping("/s1")
  String s1() {
    return "s1";
  }

  @RequestMapping("/s2")
  String s2() {
    return "s2";
  }

  @RequestMapping("/s3")
  String s3() {
    return "s3";
  }

  @RequestMapping("/s4")
  String s4() {
    return "s4";
  }

  @RequestMapping("/s5")
  String s5() {
    return "s5";
  }

  // rest mapping
  @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody String processFindBarang(HttpServletRequest request) {

    MongoDatabase database = mongoClient.getDatabase("nasa");
    MongoCollection<Document> coll = database.getCollection("test");

    String json = "{ 'test' : 'stasi' }";

    coll.insertOne(Document.parse(json));

    //coll.deleteMany(eq("test", "test"));

    MongoCursor<Document> iterator = coll.find().iterator();

    JSONArray result = new JSONArray();

    while(iterator.hasNext()) {
      JSONObject obj = new JSONObject(iterator.next().toJson());
      result.put(obj);
    }

    return result.toString();

  }

  // check user's coordinates
  @RequestMapping(value = "/checkCoordinates", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody String checkCoordinates (HttpServletRequest request, @RequestParam(value = "lat") int latitude, @RequestParam(value = "long") int longitude) {
    System.out.println("latitude: " + latitude + ", longitude: " + longitude);

    // chceck coordinates, if danger zone return alert

    return "{\n" +
            "\t\"alert\": 1,\n" +
            "\t\"livefeed\": \"https://youtu.be/iUiV4b-sOrI?t=33\",\n" +
            "\t\"image\": \"https://www.telegraph.co.uk/content/dam/news/2017/12/23/c2e8dd81-4dfe-466c-9b47-0249fbec9ab8_trans_NvBQzQNjv4BqqVzuuqpFlyLIwiB6NTmJwdCbWRXIg48_r3bhCwNyiro.jpeg?imwidth=450\",\n" +
            "\t\"instructions\": \"Find a smoke mask\",\n" +
            "\t\"escapePoint\": [23.45, 46.34],\n" +
            "\t\"polygons\": [{\n" +
            "\t\t\t\"red\": [\n" +
            "\t\t\t\t[23, 46],\n" +
            "\t\t\t\t[22, 56],\n" +
            "\t\t\t\t[33, 56]\n" +
            "\t\t\t],\n" +
            "\t\t\t\"orange\": [\n" +
            "\t\t\t\t[23, 46],\n" +
            "\t\t\t\t[22, 56],\n" +
            "\t\t\t\t[33, 56]\n" +
            "\t\t\t],\n" +
            "\t\t\t\"gray\": [\n" +
            "\t\t\t\t[23, 46],\n" +
            "\t\t\t\t[22, 56],\n" +
            "\t\t\t\t[33, 56]\n" +
            "\t\t\t]\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"red\": [\n" +
            "\t\t\t\t[23, 46],\n" +
            "\t\t\t\t[22, 56],\n" +
            "\t\t\t\t[33, 56]\n" +
            "\t\t\t],\n" +
            "\t\t\t\"orange\": [\n" +
            "\t\t\t\t[23, 46],\n" +
            "\t\t\t\t[22, 56],\n" +
            "\t\t\t\t[33, 56]\n" +
            "\t\t\t],\n" +
            "\t\t\t\"gray\": [\n" +
            "\t\t\t\t[23, 46],\n" +
            "\t\t\t\t[22, 56],\n" +
            "\t\t\t\t[33, 56]\n" +
            "\t\t\t]\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";
  }

  // report a fire
  @RequestMapping(value = "/reportFire", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody String  reportFire(HttpServletRequest request, @RequestBody String newFireReport) {
      System.out.println("new Fire Report: " + newFireReport);
      return "{\"reported\": \"true\"}";
  }

  // report problem, distress, immobility, health issues
  @RequestMapping(value = "/reportDistress", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody String  reportDistress(HttpServletRequest request, @RequestBody String reportedDistress) {
      System.out.println("new Distress Report: " + reportedDistress);
      return "{\"reportedDistress\": \"true\"}";
  }

  // get distress info for users who want to help others
  @RequestMapping(value = "/getDistressCoordinates", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody String getDistressInfo (HttpServletRequest request, @RequestParam(value = "lat") int latitude, @RequestParam(value = "long") int longitude) {
      System.out.println("Distress Location -> latitude: " + latitude + ", longitude: " + longitude);

      // chceck coordinates, if danger zone return alert

      return "  {\"lat\": 45,\n" +
              "  \"long\": 34,\n" +
              "  \"comments\": \"medical\"}";
  }

  // DISPATCHER Functionality

    // get all reports to be verified
    @RequestMapping(value = "/getAllReports", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getAllReports (HttpServletRequest request, @RequestParam(value = "lat") int latitude, @RequestParam(value = "long") int longitude) {
        System.out.println("DISPATCHER -> Get all reports for current area -> latitude: " + latitude + ", longitude: " + longitude);


        return "{\n" +
                " \t\"reports\": [{\n" +
                " \t\t\"lat\": 45,\n" +
                " \t\t\"long\": 34,\n" +
                " \t\t\"upload\": \"<base64encoded>\",\n" +
                " \t\t\"comments\": \"it's serious\"\n" +
                "        }, {\n" +
                " \t\t\"lat\": 45,\n" +
                " \t\t\"long\": 34,\n" +
                " \t\t\"upload\": \"<base64encoded>\",\n" +
                " \t\t\"comments\": \"it's serious\"\n" +
                "    }]\n" +
                "}";
    }

    // create fire
    @RequestMapping(value = "/createFire", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String  createFire(HttpServletRequest request, @RequestBody String fireToBeCreated) {
        System.out.println("Dispatcher is creating new fire: " + fireToBeCreated);
        return "{\"createdFire\": \"true\"}";
    }


    /**
     * POST /reportFire
     * {
     *      lat": 45,
     *      "long": 34,
     *      "upload": "<base64encoded>",
     *      "comments": "it's serious"
     * }
     *
     *
     * POST /distress
     *
     * {
     * 	"lat": 45,
     * 	"long": 34,
     * 	"comments": "medical"
     * }
     *
     * GET /distress
     *
     * {
     * 	"lat": 45,
     * 	"long": 34,
     * 	"comments": "medical"
     * }
     *
     *
     *
     *
     * as dispatcher
     * /GET allReports lat,long
     *
     * {
     * 	"reports": [{
     * 		"lat": 45,
     * 		"long": 34,
     * 		"upload": "<base64encoded>",
     * 		"comments": "it's serious"
     *        }, {
     * 		"lat": 45,
     * 		"long": 34,
     * 		"upload": "<base64encoded>",
     * 		"comments": "it's serious"
     *    }]
     * }
     *
     * /POST /createFire
     *
     * {
     *  "id": "1234",
     * 	"livefeed": "https://youtu.be/iUiV4b-sOrI?t=33",
     * 	"image": "https://www.telegraph.co.uk/content/dam/news/2017/12/23/c2e8dd81-4dfe-466c-9b47-0249fbec9ab8_trans_NvBQzQNjv4BqqVzuuqpFlyLIwiB6NTmJwdCbWRXIg48_r3bhCwNyiro.jpeg?imwidth=450",
     * 	"instructions": "Find a smoke mask",
     * 	"escapePoints": [
     * 		[23.45, 46.34],
     * 		[23.45, 46.34]
     * 	],
     * 	"polygon": [{
     * 		"red": [
     * 			[23, 46],
     * 			[22, 56],
     * 			[33, 56]
     * 		]
     *        }]
     * }
     *
     *
     * /POST /deleteFire/id
     *
     */
}
