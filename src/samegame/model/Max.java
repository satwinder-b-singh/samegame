package samegame.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("max")
public class Max {

    @Param(0)
    public Integer max;

    public Integer getMax() {
        return this.max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Max(Integer max) {
        this.max = max;
    }

}
