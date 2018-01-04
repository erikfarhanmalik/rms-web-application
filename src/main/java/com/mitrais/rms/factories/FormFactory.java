package com.mitrais.rms.factories;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mitrais.rms.annotations.EmailForm;
import com.mitrais.rms.annotations.NonForm;
import com.mitrais.rms.models.Employee;

@Service
public class FormFactory {

	private final Set handledType = Sets.newHashSet("String", "Integer", "LocalDate");

	public Form createEmployeeForm() {
		Form form = new Form();
		form.setAction("/employee-crud");
		form.setFormControls(Lists.newArrayList());

		Field[] allFields = Employee.class.getDeclaredFields();

		List<Field> formFields = removeNonFormFields(allFields);

		for (Field field : formFields) {
			if (handledType.contains(field.getType().getSimpleName())) {
				FormControl formControl = createFormControl(field);
				form.getFormControls().add(formControl);
			}

			handleStringEnumeratedControl(form, field);

			handleManyToOneControl(form, field);
		}

		return form;
	}

	private void handleManyToOneControl(Form form, Field field) {
		if (field.isAnnotationPresent(ManyToOne.class)) {
			FormControl formControl = createFormControl(field);
			formControl.setType("Select");
			form.getFormControls().add(formControl);
		}
	}

	private void handleStringEnumeratedControl(Form form, Field field) {
		Enumerated enumerated = field.getAnnotation(Enumerated.class);
		if (enumerated != null && EnumType.STRING.equals(enumerated.value())) {
			FormControl formControl = createFormControl(field);
			formControl.setType("Select");
			form.getFormControls().add(formControl);
		}
	}

	private FormControl createFormControl(Field field) {
		FormControl formControl = new FormControl();
		formControl.setName(field.getName());
		formControl.setType(field.getType().getSimpleName());
		formControl.setLabel(splitCamelCase(makeFirstCapital(field.getName())));
		handelRequiredControl(field, formControl);
		handleSizedControl(field, formControl);
		handleEmailControl(field, formControl);
		return formControl;
	}

	private void handleEmailControl(Field field, FormControl formControl) {
		if (field.isAnnotationPresent(EmailForm.class)) {
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
			if (!field.isAnnotationPresent(NonForm.class)) {
				formFields.add(field);
			}
		}
		return formFields;
	}

	private String makeFirstCapital(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
	
	private String splitCamelCase(String camelCaseString) {
		return camelCaseString.replaceAll(
				String.format("%s|%s|%s", 
						"(?<=[A-Z])(?=[A-Z][a-z])", 
						"(?<=[^A-Z])(?=[A-Z])",
						"(?<=[A-Za-z])(?=[^A-Za-z])"), 
						" ");
	}

}
