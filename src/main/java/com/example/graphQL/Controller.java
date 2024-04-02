package com.example.graphQL;

import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private GraphQLServiceLayer graphQLServiceLayer;

    @RequestMapping("/stockData")
    public String getPreAuthStockData(@RequestBody String query) {
        ExecutionResult executionResult = graphQLServiceLayer.initiateGraphQL().execute(query);
        Map<String, String> map = executionResult.getData();
        JSONObject jsonObject = new JSONObject(map);
        return  jsonObject.toString();
    }

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello, World!";
    }
}