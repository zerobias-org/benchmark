Use built-in features, provided by frameworks, to define bindable and non-bindable fields. An approach based on allowed fields (bindable), in which only the properties that should be updated by the user are explicitly defined, is preferable.
An architectural approach to prevent the issue is to use the *Data Transfer Object* (DTO) pattern in order to avoid direct binding. The DTO should include only the fields that are meant to be editable by the user.

