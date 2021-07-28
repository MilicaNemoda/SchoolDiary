package com.iktpreobuka.schoolEDiary.entities.DTO;

import javax.validation.constraints.NotNull;

public class SubjectDTO {
	@NotNull(message = "Name of subject must be provided.")
	private String name;
	@NotNull(message = "Weekly number of lectures must be provided.")
	private Integer weeklyNumberOfLectures;
	@NotNull(message = "School year must be provided.")
	private Integer year;
	
	public SubjectDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeeklyNumberOfLectures() {
		return weeklyNumberOfLectures;
	}

	public void setWeeklyNumberOfLectures(Integer weeklyNumberOfLectures) {
		this.weeklyNumberOfLectures = weeklyNumberOfLectures;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer schoolYear) {
		this.year = schoolYear;
	}
	
	
}
