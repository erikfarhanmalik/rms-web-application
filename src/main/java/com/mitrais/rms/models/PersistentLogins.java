package com.mitrais.rms.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "persistent_logins")
@Data
public class PersistentLogins {
	@Id
	private String series;
	private String username;
	private String token;
	private LocalDateTime last_used;
}
