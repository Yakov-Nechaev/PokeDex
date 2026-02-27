# PokeDex
![Android CI](https://github.com/Yakov-Nechaev/PokeDex/actions/workflows/android-ci.yml/badge.svg)

Aplicación Android sencilla y funcional para visualizar información sobre Pokémon, utilizando la [PokeAPI](https://pokeapi.co/).

## Stack Tecnológico

*   **UI**: Jetpack Compose (Modern Toolkit)
*   **Arquitectura**: MVVM (Model-View-ViewModel)
*   **Inyección de Dependencias**: Koin
*   **Red**: Retrofit + Gson
*   **Navegación**: Compose Navigation
*   **Carga de Imágenes**: Coil
*   **Almacenamiento Local**: SharedPreferences (gestionado a través de `StoreManager`)
*   **Otras librerías**:
    *   Palette API (para la generación de colores dinámicos en la UI basados en las imágenes)
    *   Kotlin Coroutines (asincronía)

## 🚀 Download APK

**Latest APK available in GitHub Actions**

1. Open the **Actions** tab
2. Select latest build
3. Download artifact **app-debug-apk**

## Capturas de Pantalla

<p align="center">
  <img src="screenshots/Image_1.png" width="30%" />
  <img src="screenshots/Image_2.png" width="30%" />
  <img src="screenshots/Image_3.png" width="30%" />
</p>

## Características del Proyecto

*   **Paginación**: Carga de la lista de Pokémon de forma incremental.
*   **Búsqueda**: Búsqueda de Pokémon específicos por nombre.
*   **Pantalla de Detalles**: Información detallada sobre estadísticas, tipos y características.
*   **Diseño Dinámico**: Los colores de la interfaz se adaptan al color predominante de cada Pokémon (usando Palette).
*   **Manejo de Errores**: Implementación de un `ResultWrapper` personalizado para una comunicación segura entre capas y gestión de excepciones.
*   **Inyección de Dependencias**: Configuración limpia mediante Koin (módulos: `networkModule`, `dataModule`, `viewModelModule`).

## Estructura del Proyecto

*   `data/` — Servicios API, Repositorio y StoreManager.
*   `ui/` — Componentes de Compose, temas y pantallas.
*   `vm/` — Lógica de negocio (ViewModels).
*   `models/` — Clases de datos (POJO).
*   `navigation/` — Configuración de rutas y grafo de navegación.
*   `util/` — Utilidades (Logger, ResultWrapper).
