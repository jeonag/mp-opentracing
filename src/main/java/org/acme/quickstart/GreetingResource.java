package org.acme.quickstart;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/")
public class GreetingResource {

//    private static final Logger LOG = Logger.getLogger(GreetingResource.class);
//
//    
//    
//    @Inject
//    public ConversationService conversationService;
//
//    @GET
//    @Path("/hello")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String hello() {
//        LOG.info("hello");
//        return "hello";
//    }
//
//    @GET
//    @Path("/bonjour")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String bonjour() {
//        return "bonjour";
//    }
//
//    @GET
//    @Path("/conversation")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String conversation() {
//        return conversationService.talk();
//    }
//
//    @GET
//    @Path("/foo")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String foo() {
//        return "foo";
//    }
	private long highestPrimeNumberSoFar = 2;

    @GET
    @Path("/{number}")
    @Produces("text/plain")
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public String checkIfPrime(@PathParam long number) {
        if (number < 1) {
            return "Only natural numbers can be prime numbers.";
        }
        if (number == 1) {
            return "1 is not prime.";
        }
        if (number == 2) {
            return "2 is prime.";
        }
        if (number % 2 == 0) {
            return number + " is not prime, it is divisible by 2.";
        }
        for (int i = 3; i < Math.floor(Math.sqrt(number)) + 1; i = i + 2) {
            if (number % i == 0) {
                return number + " is not prime, is divisible by " + i + ".";
            }
        }
        if (number > highestPrimeNumberSoFar) {
            highestPrimeNumberSoFar = number;
        }
        return number + " is prime.";
    }

    @Gauge(name = "highestPrimeNumberSoFar", unit = MetricUnits.NONE, description = "Highest prime number so far.")
    public Long highestPrimeNumberSoFar() {
        return highestPrimeNumberSoFar;
    }
}
