**Sprint01**

La estrategia de proyecto que hemos seguido ha estado infuluenciada por la distancia física
que tenemos entre los miembros.
Hemos considerado que la mejor opción era trabajar únicamente con una rama de proyecto para
ejecutar la aplicación directament desde un dispositivo Android (en este caso el de Pol) a causa
de la organización.
El trabajo se ha realizado mediante videollamadas y eventualmente este fin de semana nos hemos podido
reunir presencialmente para establecer mejor las bases del proyecto.

**Sprint02**

En este apartado detallaremos las contribuciones individuales de cada miembro del grupo:


T1. Implement Travel Management Logic (5 Points)

• T1.1 Implement inMemory CRUD operations for trips (addTrip, editTrip,
deleteTrip) following MVVM pattern --> Pol/Hugo

• T1.2 Implement inMemory CRUD operations for itinerary items (addActivity,
updateActivity, deleteActivity) following MVVM pattern. --> Pol/Hugo

• T1.3 Ensure proper data validation (e.g., dates must be in the future, required
fields). --> Pol

• T1.4 Implement user settings. --> Pol

• T1.5 Implement multi-language. (minimum 3 languages [en,ca,es]) --> En fase de desarrollo


T2. Design and Implement Itinerary Flow (2 Points)

• T2.1 Structure how users will interact with the itinerary. --> Hugo

• T2.2 Implement a basic UI flow for adding and modifying trip details. --> Pol

• T2.3 Ensure updates reflect dynamically in the main trip list. --> Pol


T3. Implement Data Validation and Testing (3 Points)

• T3.1 Implement basic input validation (e.g., empty fields, incorrect dates). --> Hugo

• T3.2 Write unit tests for trip and itinerary CRUD operations. --> Hugo

• T3.3 Simulate user interactions and log errors or unexpected behaviors. --> Pol

• T3.4 Update documentation with test results and fixes applied. --> Hugo/Pol

• T3.5 Add logs (to be seen in logcat) and commentaries applying good practices --> Hugo


Como se puede observar hemos intentado repartir las tareas aunque hemos participado
directamente o indirectamente en todas ambos.

**Anotaciones**

1. Nuevamente hemos tenido issues con el tema del multi-language. Tenemos implementados los values y las funciones para canviar el idioma

2. Todos los test unitarios realizados tanto de trip como de itinerary han pasado correctamente.

**Sprint03**

T1. Implement SQLite Persistence (5 Points)

• T1.1 Create Room Database class. --> Pol

• T1.2 Define Entities for Trip and ItineraryItem. Must contain at least one datetime field, text and integer. --> Hugo/Pol

• T1.3 Create Data Access Objects (DAOs) for database operations. --> Hugo

• T1.4 Implement CRUD operations using DAO for trips and itineraries. --> Hugo/Pol

T2. Integrate Room Database into the Application (3 Points)

• T2.1 Modify ViewModels to use Room Database instead of in-memory storage. --> Hugo/Pol

• T2.2 Ensure UI updates when database changes. --> Hugo/Pol

T3. Testing and Debugging (2 Points)

• T3.1 Write unit tests for DAOs and database interactions. --> No en este Sprint

• T3.2 Implement data validation (e.g., prevent duplicate trip names, check valid dates). --> Hugo/Pol

• T3.3 Use Logcat to track database operations and errors. --> Hugo

• T3.4 Update documentation with database schema and usage at design.md. --> Hugo/Pol


**Sprint04**
A causa de la separación física de los miembros del grupo, todas las tascas las he hecho yo mismo (Pol).

**Anotaciones**

1. Queda pendiente arreglar la parte visual de feedback de otras prácticas. La parte de la lógica está implementada y funciona correctamente, pero la parte visual no se ha podido implementar
   a causa de la falta de tiempo y de la separación física entre los miembros del grupo. Se ha tenido prioridad sobre la funcionalidad y no la estética visual.

2. En la implementación queda pendiente la implementación CRUD de las entidades no demandadas en el sprint.

3. Ver la base de datos en Android Studio --> users, trip, itinerary, accesslogs...

**Sprint05**
Considero que este sprint ha sido el más complicado de todos, ya que hemos tenido que implementar
mucha parte y muy difícil, también que al hacerlo solo cuesta mucho más, ya sé que has dado mucho tiempo
pero creo que hubiera valido la pena dividirlo en dos sprints. Me da rabia no haber podido hacerla como 
yo quería así que me despido del excelente, admás que te lo he entregado una semana después (hay gente que le
le dejas entregarlo al 30 de mayo).

T1. Retrofit Configuration (3 Points) DONE
• T1.1: Add Retrofit dependency and configure the HTTP client.
• T1.2: Create the necessary data models and API interfaces to access the hotel
API implementing MVVM structure.
• T1.3: Create a repository layer to abstract the API usage.
• T1.4: Create a unit test to test and connection with
/hotels/{group_id}/availability.

T2. Search and Booking Screens (4 Points)
• T2.1: Create a screen to search for hotels in London, Paris, or Barcelona, with
city, start and end dates. DONE
• T2.2: Show hotel and room data returned by the API (typically 3 rooms per
hotel). DONE
• T2.3: Allow users to book a room and save the reservation info (ID, room, hotel,
price, etc) locally in the trip table or another table creating a new trip and save
all reservation info. DONE
• T2.4: Display images of the hotel and rooms on the booking. DONE
Observation: follow the structure used in the example seen in class which implements:
- Search screen with a list of available hotels/rooms
- Hotel/room details screen with a button to book a room

T3. Add Images / Gallery to Trip (4 Points)
  • T3.1: Allow the user to attach multiple images to a trip.
  • T3.2: Save images locally in the device database or storage.
  • T3.3: Display trip-specific galleries in the trip details screen.

T4. List and Cancel Reservations (3 Points)
  • T4.1: Create a screen to list all local reservations indicating the trip related. DONE
  • T4.2: Add functionality to delete a reservation locally and via API (if required). DONE
  • T4.3: Show the associated hotel and room images in the list, similar to the
  example seen in class (RemotePersistence.zip) DONE
  • T4.4: Update the "My Trips" screen to indicate whether a trip includes a hotel
  reservation, and display the corresponding reservation details. DONE

**Anotaciones**
La parte de la galería de imágenes no se ha podido implementar a causa de la falta de tiempo y 
de la separación física entre los miembros del grupo. Esta vez me hubiera gustado otro compañero
para poder hacer esta parte. Espero que se me entienda que he hecho lo que he podido.
