package it.units.api.restricted;

import com.google.firebase.cloud.FirestoreClient;
import it.units.entities.Location;
import it.units.entities.Trip;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;


@Path("/trip")
public class TripResource {

    private final Logger log = Logger.getLogger(TripResource.class.toString());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@Context HttpServletRequest request) {

        try {
            final List<Trip> trips = FirestoreClient.getFirestore()
                    .collection("users")
                    .document(request.getAttribute("userID").toString())
                    .collection("trips")
                    .get().get().toObjects(Trip.class);

            return Response.ok(trips).build();

        } catch (InterruptedException | ExecutionException e) {
            log.severe("Error at getTrip: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setTrip(@Context HttpServletRequest request, Trip trip) {
        try {
            FirestoreClient.getFirestore()
                    .collection("users")
                    .document(request.getAttribute("userID").toString())
                    .collection("trips")
                    .document(trip.getId())
                    .set(trip).get();

            return Response.ok(trip).build();
        } catch (InterruptedException | ExecutionException e) {
            log.severe("Error at setTrip: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTrip(@Context HttpServletRequest request, Trip trip) {

/*        try {

            FirestoreClient.getFirestore()
                    .collection("users")
                    .document(request.getAttribute("userID").toString())
                    .collection("trips")
                    .document(trip.getId())
                    .set(trip).get();

            return Response.ok(trip).build();
        } catch (InterruptedException | ExecutionException e) {
            log.severe("Error at setTrip: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }*/
        return setTrip(request, trip);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrip(@Context HttpServletRequest request, Trip trip) {
        try {
            FirestoreClient.getFirestore().collection("users")
                    .document(request.getAttribute("userID").toString())
                    .collection("trips")
                    .document(trip.getId())
                    .delete().get();

            return Response.ok(trip).build();
        } catch (InterruptedException | ExecutionException e) {
            log.severe("Error at setTrip: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }




    @GET
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    public Response testPost(@Context HttpServletRequest request) {
        List<Location> lista = new ArrayList<>();
        Location location = new Location(12, 33, true, "Milano");
        lista.add(location);
        return Response.ok(lista).build();
    }


    @POST
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setTripTest(@Context HttpServletRequest request, Trip trip) {
        return Response.ok(trip).build();
    }


}