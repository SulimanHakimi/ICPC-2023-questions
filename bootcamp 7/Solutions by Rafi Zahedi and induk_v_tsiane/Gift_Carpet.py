for _ in range(int(input())):
    names_num, names_len = map(int, input().split())
    names = []
    for _ in range(names_num):
        line = input()
        names.append(line)
    vika = 'vika'
    found = 0
    for row in range(names_len):
        flag = False
        for col in range(names_num):
            if names[col][row] == vika[found]:
                flag = True
        if flag:
            found += 1
        if found == 4:
            break
    if found == 4:
        print("YES")
    else:
        print('NO')
