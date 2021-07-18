package com.iktpreobuka.schoolEDiary.entities.DTO;

public class SubjectDTO {

	private String name;
	private Integer weeklyNumberOfLectures;
	private Integer year;
	
	public SubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
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
