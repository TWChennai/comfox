package com.comfox.api;

import com.comfox.api.resources.ComfoxResource;
import com.comfox.api.services.GraphService;
import com.comfox.api.services.MessageService;
import com.thinkaurelius.titan.core.TitanGraph;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class ComfoxApiApplication extends Application<ComfoxApiConfiguration> {

    public static void main(String[] args) throws Exception {
        new ComfoxApiApplication().run(args);
    }

    public void run(ComfoxApiConfiguration configuration, Environment environment) throws Exception {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        TitanGraph titanGraph = GraphService.getTitanGraph(configuration);
        MessageService messageService = new MessageService(titanGraph);
        ComfoxResource resource = new ComfoxResource(messageService);

        environment.jersey().register(resource);
    }
}
