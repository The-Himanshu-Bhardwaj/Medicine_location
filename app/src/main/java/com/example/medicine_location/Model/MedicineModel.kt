package com.example.medicine_location.Model

data class MedicineModel (
    var name : String?,
    var row : String?,
    var drawer : String?,
    var partition : String?,
    var firebaseKey : String?
) {
    constructor() : this(null, null, null, null, null)
}
