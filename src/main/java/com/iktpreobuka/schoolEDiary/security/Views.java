package com.iktpreobuka.schoolEDiary.security;

public class Views {
	//TODO: napravi hierarhiju viewa!
	public static class Public {}
	public static class Private extends Public {}
	public static class Admin extends Private {}
}
