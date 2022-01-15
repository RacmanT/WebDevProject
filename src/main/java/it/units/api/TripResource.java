package it.units.api;

import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
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
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@Context HttpServletRequest request) {

        try {
            final List<Trip> trips = FirestoreClient.getFirestore()
                    .collection("users")
                    .document(request.getAttribute("userID").toString())
                    .collection("trips")
                    .get().get().toObjects(Trip.class);

            return Response.ok(trips.toString()).build();

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

            return Response.ok(trip.toString()).build();
        } catch (InterruptedException | ExecutionException e) {
            log.severe("Error at setTrip: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTrip(@Context HttpServletRequest request, Trip trip) {
        //final Trip trip = gson.fromJson(tripString, Trip.class);

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
        //final Trip trip = gson.fromJson(tripString, Trip.class);
        try {
            FirestoreClient.getFirestore().collection("users")
                    .document(request.getAttribute("userID").toString())
                    .collection("trips")
                    .document(trip.getId())
                    .delete().get();

            return Response.ok(trip.toString()).build();
        } catch (InterruptedException | ExecutionException e) {
            log.severe("Error at setTrip: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    private List<Trip> getTestTrip() {
        List<Trip> trips = new ArrayList<>();

        Location location1 = new Location(21.2, 21.3, false, "");
        Location location2 = new Location(122, 214.3, true, "Bologna");
        Location location3 = new Location(232, 221.3, false, "");

        ArrayList<Location> path = new ArrayList<>();
        path.add(location1);
        path.add(location2);
        path.add(location3);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(new Date());

        trips.add(new Trip(dateString, "walk", path));
        return trips;
    }

    @POST
    @Path("/test")
    @Consumes({"application/json"})
    @Produces({"application/json"})
   // @Produces(MediaType.APPLICATION_JSON)
    public Response testPost(@Context HttpServletRequest request, Location location) {
            return Response.ok("La location e importante: " + location.isImportant()).build();
    }

    @PUT
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response testPUT(String locationString) {
        try {
           // log.info("La stringa e " + locationString);
            log.info("Sem pride");
            Location location = gson.fromJson(locationString, Location.class);
            log.info("Sem tudi 2222");

            return Response.ok(location.toString()).build();
        } catch (Exception e) {
            log.severe("Error at getTrip: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

}