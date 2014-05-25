package com.github.jesty.camera.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: PropostaDiLegge
 *
 */
@Entity

public class PropostaDiLegge extends Documento implements Serializable {

	
	public PropostaDiLegge(boolean isLegge) {
		super();
		this.isLegge = isLegge;
	}

	private boolean isLegge;
	
	private static final long serialVersionUID = 1L;

	public PropostaDiLegge() {
		super();
	}
   
}
