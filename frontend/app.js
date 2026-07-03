document.addEventListener('DOMContentLoaded', () => {
    const attractionsGrid = document.getElementById('attractions-grid');
    const loadingEl = document.getElementById('loading');
    const errorEl = document.getElementById('error-message');
    const refreshBtn = document.getElementById('refresh-btn');

    // Default mock data in case backend is down or empty (for premium showcase)
    const mockAttractions = [
        { name: "Thunderbird", description: "A high-speed launched coaster that takes you through 3 inversions.", status: "Operational", thrillLevel: "Extreme" },
        { name: "The Drop", description: "A 300ft vertical drop tower.", status: "Maintenance", thrillLevel: "High" },
        { name: "Lazy River", description: "Relax and float along our scenic park river.", status: "Operational", thrillLevel: "Low" },
        { name: "Giga Coaster", description: "Experience zero gravity on this 300ft tall monster.", status: "Operational", thrillLevel: "Extreme" }
    ];

    const fetchAttractions = async () => {
        // Show loading state
        attractionsGrid.classList.add('hidden');
        errorEl.classList.add('hidden');
        loadingEl.classList.remove('hidden');

        try {
            // Hit the API through the Ingress route
            const response = await fetch('/coasters/AttractionServlet');
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            let data = await response.json();
            
            // If data is empty (no coasters in DB yet), use mock data for the showcase
            if (!data || data.length === 0) {
                console.log("No attractions found in DB, using mock data for display.");
                data = mockAttractions;
            }

            renderAttractions(data);

        } catch (error) {
            console.error('Error fetching attractions:', error);
            // Fallback to mock data for demonstration if backend fails
            console.log("Backend connection failed, displaying mock data for showcase.");
            renderAttractions(mockAttractions);
        }
    };

    const renderAttractions = (attractions) => {
        loadingEl.classList.add('hidden');
        attractionsGrid.innerHTML = '';
        
        attractions.forEach((attr, index) => {
            const card = document.createElement('div');
            card.className = 'card';
            // Stagger animations
            card.style.animationDelay = `${index * 0.1}s`;
            
            // Handle potentially different JSON keys from the actual Java backend
            const name = attr.name || attr.attractionName || attr.title || attr.AttractionName || 'Unknown Attraction';
            const desc = attr.description || attr.details || 'No description available.';
            const status = attr.status || attr.state || 'Operational';
            const thrill = attr.thrillLevel || attr.intensity || 'Varies';

            card.innerHTML = `
                <h4>${name}</h4>
                <p>${desc}</p>
                <div class="meta">
                    <span class="status">Status: ${status}</span>
                    <span class="thrill">Thrill: ${thrill}</span>
                </div>
            `;
            attractionsGrid.appendChild(card);
        });
        
        attractionsGrid.classList.remove('hidden');
    };

    refreshBtn.addEventListener('click', fetchAttractions);

    // --- Modal & Form Logic ---
    const buyBtn = document.getElementById('buy-ticket-btn');
    const modal = document.getElementById('ticket-modal');
    const closeBtn = document.querySelector('.close-btn');
    const ticketForm = document.getElementById('ticket-form');
    const formStatus = document.getElementById('form-status');

    buyBtn.addEventListener('click', () => {
        modal.classList.remove('hidden');
        formStatus.textContent = '';
        formStatus.className = 'status-msg';
    });

    closeBtn.addEventListener('click', () => {
        modal.classList.add('hidden');
    });

    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.classList.add('hidden');
        }
    });

    ticketForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        formStatus.textContent = "Processing...";
        formStatus.className = "status-msg";
        
        const customerID = parseInt(document.getElementById('customerId').value);
        const accessLevel = parseInt(document.getElementById('accessLevel').value);
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        const payload = { customerID, accessLevel, startDate, endDate };

        try {
            const response = await fetch('/coasters/TicketServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (response.status === 201 || response.ok) {
                formStatus.textContent = "Ticket purchased successfully!";
                formStatus.className = "status-msg success";
                ticketForm.reset();
                setTimeout(() => {
                    modal.classList.add('hidden');
                }, 2000);
            } else {
                formStatus.textContent = "Failed to purchase ticket. Error code: " + response.status;
                formStatus.className = "status-msg error";
            }
        } catch (err) {
            formStatus.textContent = "Network error. Please try again.";
            formStatus.className = "status-msg error";
        }
    });

    // Initial fetch
    fetchAttractions();
});
