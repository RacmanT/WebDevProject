package it.units.api.unrestricted;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import it.units.entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/*
Wrapper class used to add a new User on Firebase Authentication and
Firebase Firestore database. A more elegant way could consist in using
Firebase Cloud Functions to enrich signInWithEmailAndPassword() method called
from frontend but is not available for free nor allow free trial.
*/

@Path("/register")
public class Auth {

    private final Logger log = Logger.getLogger(Auth.class.toString());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(final User user) {

        //final User user = gson.fromJson(userString, User.class);
        try {
            // https://firebase.google.com/docs/auth/admin/manage-users#create_a_user
            // Adding user to Firebase Authentication
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword())
                    .setDisplayName(user.getUsername());

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

            // Adding user to Firebase Firestore database
            FirestoreClient.getFirestore()
                    .collection("users")
                    //.document(userRecord.getUid()).set(Map.of("name", user.getUsername())).get();
                    .document(userRecord.getUid())
                    .set(new HashMap<String, String>() {{
                        put("name", user.getUsername());
                    }}).get();


            return Response.ok("Successfully created new user: " + userRecord.getUid()).build();
        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            log.severe("Error at addUser: " + e.getMessage());
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

    @GET
    public Response testUser() {
        return Response.ok().build();
    }


}
