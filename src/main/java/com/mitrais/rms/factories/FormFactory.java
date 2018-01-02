package com.mitrais.rms.factories;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mitrais.rms.annotations.EmailForm;
import com.mitrais.rms.annotations.NonForm;
import com.mitrais.rms.models.Employee;

public class FormFactory {

	private static final Set handledType = Sets.newHashSet("java.lang.String", "java.lang.Integer",
			"java.lang.LocalDate");

	public static Form createEmployeeForm() {
		Form form = new Form();
		form.setAction("someaction");
		form.setFormControls(new HashMap<>());

		Field[] allFields = Employee.class.getDeclaredFields();
		List<Field> formFields = removeNonFormFields(allFields);
		
		for (Field field : formFields) {
			if (handledType.contains(field.getType().getName())) {
				FormControl formControl = createFormControl(field);
				form.getFormControls().put(field.getName(), formControl);
			}
			
			handleStringEnumeratedControl(form, field);
			
			handleManyToOneControl(form, field);
		}

		return form;
	}

	private static void handleManyToOneControl(Form form, Field field) {
		if (field.isAnnotationPresent(ManyToOne.class)) {
			FormControl formControl = new FormControl();
			formControl.setName(field.getName());
			formControl.setType("Select");
			form.getFormControls().put(field.getName(), formControl);
		}
	}

	private static void handleStringEnumeratedControl(Form form, Field field) {
		Enumerated enumerated = field.getAnnotation(Enumerated.class);
		if (enumerated != null && EnumType.STRING.equals(enumerated.value())) {
			FormControl formControl = new FormControl();
			formControl.setName(field.getName());
			formControl.setType("String");
			form.getFormControls().put(field.getName(), formControl);
		}
	}

	private static FormControl createFormControl(Field field) {
		FormControl formControl = new FormControl();
		formControl.setName(field.getName());
		formControl.setType(field.getType().getSimpleName());
		handelRequiredControl(field, formControl);
		handleSizedControl(field, formControl);
		handleEmailControl(field, formControl);
		return formControl;
	}

	private static void handleEmailControl(Field field, FormControl formControl) {
		if(field.isAnnotationPresent(EmailForm.class)) {
			formControl.setType("email");
		}
	}

	private static void handleSizedControl(Field field, FormControl formControl) {
		Size size = field.getAnnotation(Size.class);
		if (size != null) {
			formControl.setLength(size.max());
		}
	}

	private static void handelRequiredControl(Field field, FormControl formControl) {
		if (field.isAnnotationPresent(NotNull.class)) {
			formControl.setRequired(true);
		} else {
			formControl.setRequired(false);
		}
	}

	private static List<Field> removeNonFormFields(Field[] allFields) {
		List<Field> formFields = Lists.newArrayList();
		for (Field field : allFields) {
			if(!field.isAnnotationPresent(NonForm.class)) {
				formFields.add(field);
			}
		}
		return formFields;
	}

}
