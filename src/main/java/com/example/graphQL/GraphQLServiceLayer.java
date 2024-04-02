package com.example.graphQL;

import com.example.graphQL.Repositories.StockRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class GraphQLServiceLayer {

    //Holds all data of the schema and runtime wiring
    private GraphQL graphQL;

    //Database layer to fetch the data the user wants
    @Autowired
    private StockRepository stockRespository;

    //load the graphql schema file present
    @Value("schema.graphql")
    private ClassPathResource classPathResource;

    //Method is loaded at runtime to serve the data
    @PostConstruct
    private void loadSchema() throws IOException {
        InputStream inputStream = classPathResource.getInputStream();
        Reader targetReader = new InputStreamReader(inputStream);

        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(targetReader);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();

        //compiling a graphql schema file
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    //maps the incoming request from the client (user query) to our backend Repo layer and does the magic.

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring->typeWiring.dataFetcher("stocks", stockRespository)).build();
    }

    public GraphQL initiateGraphQL() {
        return graphQL;
    }
}
