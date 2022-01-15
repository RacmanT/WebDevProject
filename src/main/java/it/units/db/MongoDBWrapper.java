package it.units.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class MongoDBWrapper {

    /*
    private static MongoDBWrapper instance = null;
    private final String connectionString = "mongodb://127.0.0.1:27017";
    private MongoClient mongoClient;

    private MongoDBWrapper() {
        try {
            mongoClient = MongoClients.create(connectionString);
        } catch (Exception e) {
            System.out.println("ERROR!!");
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static MongoDBWrapper getInstance() {
        if (instance == null) {
            instance = new MongoDBWrapper();
        }
        return instance;
    }

    public List<String> getTrips() {
        try {
            MongoDatabase bookDB = mongoClient.getDatabase("biblio");
            MongoCollection<Document> bookCollection = bookDB.getCollection("biblio");
            List<Document> books = bookCollection.find().into(new ArrayList<Document>());
            List<String> booksJson = new ArrayList<>(books.size());
            for(Document b:books){
                booksJson.add(b.toJson());
            }
            return booksJson;
        } catch (Exception e) {
            System.out.println("ERROR!!");
            System.out.println(e.getStackTrace());
            return Collections.singletonList(e.toString());
        }
    }

    public String getBook(int id) {
        try {
            MongoDatabase bookDB = mongoClient.getDatabase("biblio");
            MongoCollection<Document> bookCollection = bookDB.getCollection("biblio");
            Document book = bookCollection.find(new Document("id", id)).first();
            return book.toJson();
        } catch (Exception e) {
            System.out.println("ERROR!!");
            System.out.println(e.getStackTrace());
            return e.toString();
        }
    }

    public String addBook(Book book){
        try {
            MongoDatabase bookDB = mongoClient.getDatabase("biblio");
            MongoCollection<Document> bookCollection = bookDB.getCollection("biblio");
            Document idMaxDoc = bookCollection.find().sort(new Document("id",-1)).first();
            int idMax = idMaxDoc.getInteger("id");
            Document b = new Document();
            b.append("id", idMax+1)
                    .append("titolo", book.getTitolo())
                    .append("autore", book.getAutore())
                    .append("anno", book.getAnno());
            bookCollection.insertOne(b);
            return b.toJson();
        } catch (Exception e) {
            System.out.println("ERROR!!");
            System.out.println(e.getMessage());
            return e.toString();
        }
    }

    public String updateBook(Book book){
        try {
            MongoDatabase bookDB = mongoClient.getDatabase("biblio");
            MongoCollection<Document> bookCollection = bookDB.getCollection("biblio");
            Bson filter = eq("id", Integer.parseInt(book.getId()));
            Bson updateTitle = set("titolo", book.getTitolo());
            Bson updateAuthor = set("autore", book.getAutore());
            Bson updateYear = set("anno", book.getAnno());
            Bson updateOperations = combine(updateTitle, updateAuthor, updateYear);
            UpdateResult updateOne = bookCollection.updateOne(filter, updateOperations);
            Document b = bookCollection.find(filter).first();
            return b.toJson();
        } catch (Exception e) {
            System.out.println("ERROR!!");
            System.out.println(e.getMessage());
            return e.toString();
        }
    }

*/

}
