package com.iktpreobuka.schoolEDiary.entities;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

//U prevodu na srpski - razredni staresina
public class ClassHeadTeacherEntity extends TeacherEntity {
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "classRoom")
	protected ClassRoomEntity classRoom;
}
