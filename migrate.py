import os

for root, _, files in os.walk('c:\\RollerCoasterPark-Project2-master'):
    for file in files:
        if file.endswith('.java'):
            path = os.path.join(root, file)
            with open(path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            new_content = content.replace('javax.servlet', 'jakarta.servlet')
            
            if 'servlets' in root and 'resp' in new_content and 'printStackTrace' in new_content:
                # Add resp.setStatus(400) to e.printStackTrace() if inside servlets/
                new_content = new_content.replace('e.printStackTrace();', 'e.printStackTrace();\n                resp.setStatus(400);')
            
            if new_content != content:
                with open(path, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print(f"Migrated {path}")
