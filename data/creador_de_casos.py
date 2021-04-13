import random

marcos = 25
paginas = 100
porcentaje = 0.80

f = open("referencias33.txt", "w")

f.write("{}\n".format(marcos))
f.write("{}\n".format(paginas))
f.write("{}\n".format(porcentaje))

i = 0
while i<1000:
    if i<1000*porcentaje:
        f.write("{}\n".format(random.randint(0, paginas*0.25)))
        i+=1
        continue
    f.write("{}\n".format(random.randint(0, paginas-1)))
    i+=1

f.close()
