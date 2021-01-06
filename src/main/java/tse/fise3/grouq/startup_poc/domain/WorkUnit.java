package tse.fise3.grouq.startup_poc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Duration;

/**
 * A WorkUnit.
 */
@Entity
@Table(name = "work_unit")
public class WorkUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Duration duration;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "workUnits", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "workUnits", allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public WorkUnit duration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public WorkUnit date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public WorkUnit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public WorkUnit user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public WorkUnit project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkUnit)) {
            return false;
        }
        return id != null && id.equals(((WorkUnit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkUnit{" +
            "id=" + getId() +
            ", duration='" + getDuration() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
