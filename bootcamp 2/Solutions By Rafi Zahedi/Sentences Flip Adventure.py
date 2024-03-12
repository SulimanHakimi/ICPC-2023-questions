# if you fight for it, you deserve it.
# if you deserve it, fight for it.
def flip_sentence(sentence):
    words = sentence.split()
    reversed_sentence = ' '.join(reversed(words))
    return reversed_sentence


while True:
    sentences = []

    while True:
        line = input().strip()
        if line == '*':
            break
        sentences.append(line)

    if sentences:
        for sentence in sentences:
            flipped_sentence = flip_sentence(sentence)
            print(flipped_sentence)

    print('---')

    if line == '*':
        break
