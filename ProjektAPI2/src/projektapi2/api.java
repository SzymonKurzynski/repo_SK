/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projektapi2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author egzamin
 */
public class api {
    ArrayList<String> list = new ArrayList<>();
    
    public ArrayList<String> getDate(int year) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://numbersapi.p.rapidapi.com/"+year+"/year?json=true&fragment=true"))
		.header("X-RapidAPI-Key", "6b42755e4cmsh3e0b8598720f453p15ec00jsn8d350622273b")
		.header("X-RapidAPI-Host", "numbersapi.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        list = convertJSON(response.body());
        return list;
    }
    
    public ArrayList<String> convertJSON(String bodyResponse) {
    JSONObject jsonObject = new JSONObject(bodyResponse);

    // Check if the "date" key is nested under another key
    if (jsonObject.has("year") && jsonObject.getJSONObject("year").has("date")) {
        list.add(jsonObject.getJSONObject("year").getString("date"));
    } else {
        // If not nested, directly access the "date" key
        list.add(jsonObject.getString("date"));
    }

    // Similarly, check and add the "text" key
    if (jsonObject.has("year") && jsonObject.getJSONObject("year").has("text")) {
        list.add(jsonObject.getJSONObject("year").getString("text"));
    } else {
        list.add(jsonObject.getString("text"));
    }

    return list;
}
}