package fr.adaming.managedbeans;
 
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
/**
 * Gestionnaire des images sur la page d'accueil
 */
@ManagedBean(name="imgMB")
public class ImageManagedBean {
     
    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 6; i++) {
            images.add("produit" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}