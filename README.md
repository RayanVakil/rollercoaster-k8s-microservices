# RollerCoasterPark

A full-stack web application with the purpose of making Theme Park management much easier. Granting customers the ability to view available attractions, and purchase tickets. Employees the ability to log in to inform maintenance of any issues with a rollercoaster, and the management basic employee modification capabilities, and the ability to shop for new Attractions.

## Architecture & Technology
- **Backend Services:** Implements RESTful API calls in Java.
- **Containerization:** Modern multi-stage Docker builds for minimal, secure artifact packaging.
- **Orchestration:** Cloud-native Kubernetes manifests (`networking.k8s.io/v1` compliant), complete with Resource Requests/Limits, Readiness/Liveness Probes, and Secrets Management.
- **CI/CD:** Automated build and push pipeline using **GitHub Actions**.

*Note: This project was originally built for learning DevOps pipelines and container orchestration, and was modernized in 2026 to follow 12-factor app security principles and current Kubernetes best practices.*
