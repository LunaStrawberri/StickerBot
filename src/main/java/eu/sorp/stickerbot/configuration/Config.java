package eu.sorp.stickerbot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sorp
 */
public final class Config {
    
    private final String fileName;
    private final File file;
    private JSONObject jsonObject;
    
    public Config(String fileName){
        this.fileName = fileName;
        this.file = new File(fileName);
        
        if(this.file.exists()){
            try {
                final JSONParser parser = new JSONParser();
                this.jsonObject = (JSONObject) parser.parse(new FileReader(fileName));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ParseException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.jsonObject = new JSONObject();
            this.jsonObject.put("bot-token", "BOT-TOKEN");
            this.save();
        }
        
    }
    
    public void save(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonObject);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getFile() {
        return file;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
    
    public String getFileName() {
        return fileName;
    }
    
}
